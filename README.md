# Android-CustomGlide
一个使用方法模仿Glide的图片加载库



## Features
 - 每次request的lifecycle，网络请求，异步回都是独立的模块
 - 支持网络，本地资源文件加载
 - 支持ImageView或者ViewGroup作为显示target
 - 支持网络下载图片进度回调
 - 支持Gif显示（使用android-gif-drawable，缓存暂不支持）
 - 支持自定义URl
 - 支持bitmap转换
 - 支持预加载


 
## Installation
### Gradle Dependency
#####   Add the library to your project build.gradle
```gradle


```

## How to use?




 
## Sample Usage

### 1（从服务器加载图片，指定memory和disk缓存）


```java
Glide.with(MainActivity.this)
		.load(url)
		.placeholder(R.drawable.placeholder)
		.error(R.drawable.error)
		.memoryCacheStrategy(new LocalDataSource.MemoryCacheStrategy(true, 60))
		.diskCacheStrategy(new LocalDataSource.DiskCacheStrategy(true,true))
		.into(imv1);

```

### 2（从服务器加载图片，注册监听器）


```java
Glide.with(MainActivity.this)
		.load(url)
		.listener(new DataSource.LoadDataListener() {
			@Override
			public void onLoadStarted() {
				GLog.printInfo("onLoadStarted");
			}

			@Override
			public void onDataLoaded(Object resource) {
				GLog.printInfo("onResourceReady");
			}

			@Override
			public void onDataLoadedError(@NonNull Throwable throwable) {
				GLog.printInfo("onException, " + throwable.getMessage());
			}

			@Override
			public void onProgressUpdate(int value) {
				GLog.printInfo("onProgressUpdate, value" + value);
			}

			@Override
			public void onCancelled() {
				GLog.printInfo("onCancelled");
			}
		}).into(imv1);

```
### 3（指定bitmap转换器，CircleCrop 继承BitmapTransformation）


```java
Glide.with(MainActivity.this)
		.load(url)
		.transform(new CircleCrop(MainActivity.this))
		.into(imv1);

```

### 4（从本地加载图片）


```java
Glide.with(MainActivity.this)
		.load(file)
		.into(imv1);

```

### 5（加载图片到ViewGroup）


```java
Glide.with(MainActivity.this)
		.load(url)
		.into(new SimpleDrawableViewTarget(lin));

```

### 6（预加载）


```java
Glide.with(MainActivity.this)
		.load(url)
		.preload（）;

```

### 5（自定义URl，继承RequestOrder<String>）


```java
Glide.with(MainActivity.this)
		.load(new MyUrl(url))
		.preload（）;

```

## License

    Copyright 2017 MyJoybar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.    
        