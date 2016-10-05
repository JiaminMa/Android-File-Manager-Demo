# Android-File-Manager-Demo
This is a copy of SolidExplorer and achieved 50% of it.

It's a simple file manager demo designed by metarial design. I just copy it to study some new features above Android Lolipop.For example, floating button, navigationView , recylerView and some metarial design visualization.

##UI

<img src="https://github.com/JiaminMa/Android-File-Manager-Demo/blob/master/screenshot/fm1.png" width = "200" height = "300" alt="image" align=center />
<img src="https://github.com/JiaminMa/Android-File-Manager-Demo/blob/master/screenshot/fm2.png" width = "200" height = "300" alt="image" align=center />
<img src="https://github.com/JiaminMa/Android-File-Manager-Demo/blob/master/screenshot/fm3.png" width = "200" height = "300" alt="image" align=center />
<img src="https://github.com/JiaminMa/Android-File-Manager-Demo/blob/master/screenshot/fm4.png" width = "200" height = "300" alt="image" align=center />
<img src="https://github.com/JiaminMa/Android-File-Manager-Demo/blob/master/screenshot/fm5.png" width = "200" height = "300" alt="image" align=center />
<img src="https://github.com/JiaminMa/Android-File-Manager-Demo/blob/master/screenshot/fm6.png" width = "200" height = "300" alt="image" align=center />
<img src="https://github.com/JiaminMa/Android-File-Manager-Demo/blob/master/screenshot/fm7.png" width = "200" height = "300" alt="image" align=center />

##Fragment Stack
It's really an interesting thing I've created. I used the fragment to display one folder, so the fragment would be created much if the user accessed too many folders. So I've created a fragment stack to manage these fragment like the activity stack. Actually, I implemented the fragment stack by use a List and a Map. List is used to simulate a stack rather than ths Stack in JDK because there exists some operations of move the elment from middle to top. So the fragment stack is a specfic stack. And the Map is to record the reference of the fragment. Because a fragment implies a file path, so the fragment should be single. So I record the fragment by the path, in a word, Key is the file path and Value is fragment reference.

See the data structure like this:

```java
public class FragmentStack {

    private List<BaseFragment> mFragmentStack;
    private Map<String, BaseFragment> mFragmentMap;
}
```

##Others
Some personal libs like FileOperationManager, ApkUtils, etc are done in package utils.
