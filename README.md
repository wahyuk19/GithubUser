# GithubUser

How to run using Android Studio:
1.after cloned and loaded the project to android studio,you can directly run the project to emulator or real device
2.also you can build the apk first in the android studio then install with adb or vysor
3.you can check the local datas in app inspection

features
-get all users from database
-search users
-details with repos lists(only page 1)

library
-retrofit
-okhttp
-glide
-room
-pagingroom
-hilt
-kotlin coroutines
-chucker

challenges
-first time using pagingroom,the increment value for nextpage request need remote keys

architexture
-using MVVM because usually use it personally and for separate business logic and presentation,and viewmodel as the bridge

