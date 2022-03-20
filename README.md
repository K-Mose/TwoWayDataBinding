# TwoWayDataBinding
**Android Two-way DataBinding : <a href="https://developer.android.com/topic/libraries/data-binding/two-way?hl=ko">양방향 데이터 결합</a><br>**
LiveData & ViewModel(+ViewModelFactory)을 이용한 DataBinding

## <a href="https://proandroiddev.com/advanced-data-binding-binding-to-livedata-one-and-two-way-binding-dae1cd68530f">Advanced Data Binding : Binding to LiveData<a>
많은 안드로이드의 앱들이 MVVM으로 사용됨에 따라 Data Bindng의 사용은 더 증가되었습니다. Data Binding은 layout내의 UI 컴포넌트와 결합하여 앱의 data source의 사용을 프로그래밍적 보다 선언형식으로 사용 할 수 있도록 허용합니다. 자동적으로 UI안에 데이터의 변경 내용을 알리고 UI 속성의 변경 내용이 전해지는 것을 얻기 위해 LiveData와 함께 사용합니다. 또 화면의 재생성에서도 UI가 가지고 있던 데이터를 유지시켜야 하는데, 여기서 가장 좋은 방법은 ViewModel에게 UI관련 데이터를 위임하여 보존시키는 것 입니다. 
  
**Why binding to LiveData?**<br>
LiveData를 사용한 이점

* Activity가 멈춤으로 생기는 크러쉬가 없음 - 옵저버의 lifecycle이 활동하지 않는다면 더이상 어떠한 LiveData의 이벤트를 받지 않습니다.
* 적절한 환경설정 변경 - 만약 Activity나 Fragment가 환경설정 변경(화면 회전과 같은)으로인해 재생성된다면, 즉각적으로 최신 데이터를 전달받을 수 있습니다.
* 메모리 누수 없음 - subscription을 수동으로 폐기할 일 없이 연관된 lifecycle이 파괴되다면 옵저버가 알아서 정리합니다. 
<br>

## <a href="https://developer.android.com/reference/androidx/lifecycle/LiveData">LiveData </a>
LiveData는 주어진 lifecycle안에서 관찰될 수 있는 Data holder입니다. <br>
그 말인즉슨 <a href="/reference/android/arch/lifecycle/Observer">Observer</a>가 <a href="/reference/android/arch/lifecycle/LifecycleOwner">LifecycleOwner</a>와 쌍으로 추가될 수 있습니다. <br>
그리고 Observer는 쌍의 LifeCycleOnwer가 활동상태일 때 감싸진 데이터의 변화를 알릴 수 있습니다. 
(LifecycleOwner의 상태가 STARTED나 RESUMED일 때 활동상태로 여겨집니다.)<br>
LiveData 클래스는 ViewModel의 개별적인 데이터 필드를 갖을 수 있도록 설계되었을 뿐만 아니라, 
앱 안에서 결합이 적은 형태의 서로 다른 모듈사이에서 데이터를 공유할 수 있게 합니다. 
<br>
  
**LiveData vs ObservableFields**<br>
ObservableFiled와 같이 Observable을 구현하는 객체들과는 달리 LiveData 객체는 lifecycle의 옵저버에 등록된 데이터 변화를 알 수 있습니다. 
* 수동으로 lifecycle을 처리할 필요가 없음 : UI 컴포넌트는 연관있는 data를 관찰하기만 하고 멈추거나 중단하지 않습니다. LiveData는 
  연관된 lifecycle의 상태변화의 인지를 관찰하는 관찰할 때 부터 자동적으로 관리합니다. 
* Transformations와 MediatorLiveData로 인한 더 많은 기능들 : LiveData의 사용은 <a href="https://developer.android.com/reference/androidx/lifecycle/Transformations">Transformations</a>와
  <a href="https://developer.android.com/reference/androidx/lifecycle/MediatorLiveData">MediatorLiveData</a>의 기능들을 사용할 수 있습니다. 
  만약 관찰되는 여러 view들이 존재해도 하나의 MediatorLiveData로 관찰 가능합니다. 
* 자원 공유 : LiveData를 확장하여 커스텀 객체를 생성하는 것은 System service에 한번 연결하는 것을 허용하여, 자원을 필요로하는 어떠한 옵저버가 객체를 관찰 가능하게 합니다.
 
<br>

## <a href="https://developer.android.com/topic/libraries/architecture/viewmodel">ViewModel</a>
ViewModel 클래스는 UI 관련 데이터를 저장하고 관리하도록 설계되어, UI 컨트롤러 로직에서 뷰 데이터 소유권을 분리해 더 쉽고 효율적으로 데이터를 관리 할 수 있습니다. <br>
* ViewModel 객체는 LiveData와 같은 LifecycleObservers를 포함 할 수 있지만, ViewModel은 변화를 감지하지 않고 LifecycleOnwer에서 수행이됩니다. 
```kotlin
// https://developer.android.com/topic/libraries/architecture/viewmodel#implement
class MyViewModel : ViewModel() {
  fun getUsers(): LiveData<List<User>> {
      return users
  }
…
}
  
class MyActivity : AppCompatActivity() {  
  override fun onCreate(savedInstanceState: Bundle?) {
    …
    val model: MyViewModel by viewModels()
    model.getUsers().observe(this, Observer<List<User>>{ users ->
        // update UI
    })
  }
}
```  
 *위의 예 처럼 ViewModel 객체는 LiveData 객체를 갖고, LiveData 객체의 Observer가 변화를 감지합니다*
  <br><br>
**LifeCycle of ViewModel**<br>
ViewModel 객체의 범위는 ViewModel을 가져올 때 ViewModelProvider에 전달되는 lifecycle로 지정됩니다. 
ViewModel은 범위가 지정된 lifecycle이 완전히 끝날 때 까지(Activity 활동 종료 또는 Fragement의 분리) 메모리에 남아있습니다. 
<img src="https://developer.android.com/images/topic/libraries/architecture/viewmodel-lifecycle.png"><br>
일반적으로 시스템에서 Activty의 onCreate() 메서드가 처음 호출될 때 ViewModel을 요청하고, Acitivy가 onDestory() 될 때까지 ViewModel은 존재하며, ViewModel이 더이상 사용되지 않게된다면 ViewModel의 onCleared()를 호출하여 폐기합니다. 
<br>  
<br>
**ViewModel with ViewModelProvider.Factory**
*추가 예정*
  
<br>  

# Two-way Data Binidng
양뱡향 데이터 바인딩은 기존 데이터 바인딩에서 아래와 같이 변경하기만 하면 됩니다. 
```xml
          <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="40sp"
            android:text="@{viewModel.userName}"
            ……. />
```
@{} 에서 @={} 로 변환
```xml
          <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="40sp"
            android:text="@={viewModel.userName}"
            ……. />
```
  <br>
EditText에 userName이라는 Live데이터가 연결되고, userName이 변경될 때 EditText를 리스너로 사용하기 위해 @={}로 표기합니다. 
@={} 표기법은 속성과 관련된 데이터 변경사항을 받는 동시에 사용자 업데이트를 수신 대기합니다.
  
## Preview
  text | number
  -----|----
  ![20220320_154409](https://user-images.githubusercontent.com/55622345/159151645-e86d5416-8229-4eb4-a82d-1de4c33f3e83.gif)|![20220320_154619](https://user-images.githubusercontent.com/55622345/159151652-cdcca351-224d-4a63-acbd-7dc8b18b9128.gif)



## Ref.
  * https://developer.android.com/topic/libraries/data-binding?hl=ko
  * https://developer.android.com/topic/libraries/data-binding/two-way?hl=ko  
  * https://proandroiddev.com/advanced-data-binding-binding-to-livedata-one-and-two-way-binding-dae1cd68530f
  * https://medium.com/mindorks/livedata-viewmodel-making-your-own-magic-73facb06fbb
  * https://medium.com/koderlabs/viewmodel-with-viewmodelprovider-factory-the-creator-of-viewmodel-8fabfec1aa4f
