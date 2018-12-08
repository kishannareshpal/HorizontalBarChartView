# HorizontalBarChartView
This library allows you to show a set of data over a horizontal bar. Just like the ones you see for example, in a device's Storage Usage.

[![](https://jitpack.io/v/kishannareshpal/StorageDetailsView.svg)](https://jitpack.io/#kishannareshpal/StorageDetailsView) [![API 14](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/6befaa33fa6c465a9be7591cbe162257)](https://app.codacy.com/app/kishannareshpal/StorageDetailsView?utm_source=github.com&utm_medium=referral&utm_content=kishannareshpal/StorageDetailsView&utm_campaign=Badge_Grade_Dashboard)



![Screenshot](https://github.com/kishannareshpal/StorageDetailsView/raw/master/1.png)



## Setup

To begin using **HorizontalBarChartView** in your projects please follow the steps below for either *Gradle* or *Maven*.

##### a) Gradle

1. Open `build.gradle` file for your application and add this to the end of **repositories { ... }** section:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

2. Add the library to the **dependencies { ... }** section of your **app** level `build.gradle` file:

```groovy
dependencies {
    // ...
    implementation 'com.github.kishannareshpal:HorizontalBarChartView:0.1.0'
    // ...
}
```



##### b) Maven

1. Add the repository to your `build.gradle` file:

   ```xml
   <repositories>
       <repository>
           <id>jitpack.io</id>
           <url>https://jitpack.io</url>
       </repository>
   </repositories>
   ```

2. Add the library as a dependency to your **app** level `build.gradle` file:

   ```xml
   <dependency>
       <groupId>com.github.kishannareshpal</groupId>
       <artifactId>HorizontalBarChartView</artifactId>
       <version>0.1.0</version>
   </dependency>
   ```






## Getting Started

**HorizontalBarChartView** can be implemented as a custom view to any **layout**. For more detailed code example to use the library, please refer to the sample [`/app`](https://github.com/kishannareshpal/StorageDetailsView/tree/master/app) .

```xml
<horizontalbarchartview.StorageDetailsView
	android:id="@+id/myChart"
	android:layout_width="match_parent"
    android:layout_height="12dp" />
```

![Default look](https://github.com/kishannareshpal/StorageDetailsView/raw/master/2.png)

The view by default, will have a grey colored background and round corners. In order to customize it, you can use the attributes listed on the table below:

#### Optional customizations

| Name             | Type  | Description                                                  |
| :--------------- | :---- | ------------------------------------------------------------ |
| sdv_color        | color | allows you to specify the default bar color. **Default value: #F5F5F5 **(Very light grey) |
| sdv_cornerRadius | float | allows you to specify the radius of all four corners. **Default value: 14F** |





## How to's

**1. Add new data over the bar**

```java
// Add data...
void addData(int dataId, float percentage, int colorRes);

// Then call .show() at the end, to update your bar with new data:
void show();
```

Data are the visual representation that fills up the bar on however percentage you set between 0 and 100%.

In order to add a new data, use the `addData()` method to add as much as you want (but the *percentage* argument should not exceed a total of 100%). Once you've done adding the data, call the `show()` method to update the chart with the new data..

The  `addData()` arguments:

- **dataId** - An arbitrary id, so you can query it's information later, such as it's percentage via the `getDataPercentage()` method.

- **percentage** - How much of the bar this data will fill, starting from the last added data. The total percentage of the added data *should not exceed 100%*. E.g: *`float 42F | int 42`.* 

- **colorRes** - the background color of this new detail. E.g: *I recommend using these two methods for retrieving the colors: `ContextCompat.getColor(getContext(), R.color.blue)` or `getResources().getColor()`.*


:egg:Example:

```java
// Define and initialize the view
HorizontalBarChartView hbcv = findViewById(R.id.hbcv);

// Add the data.
int percent = 12;
int color = ContextCompat.getColor(getContext(), R.color.blue);
hbcv.addData(MEDIA_ID, percent, color);

int percent = 35;
int color = ContextCompat.getColor(getContext(), R.color.green);
hbcv.addData(APPS_ID, percent, color);

...
// And finally call the .show() to update the bar.
sdv.show();
```

![Screenshot 2](https://github.com/kishannareshpal/StorageDetailsView/raw/master/3.png)



##### 2. Getter methods

```java
// Returns the percentage of an added data via its Id:
float getDataPercentage(int dataId);

// Returns the list of all added data.
// MyData.class contains `int ID` and `float PERCENTAGE`.
List<MyData> getAllData();
```

:egg:Example:

```java
// ... based of the last example, and continuing from it ...

// to get the APPS_ID percentage:
float apps_percentage = hbcv.getDataPercentage(APPS_ID);
Log.v("hbcv", "Your apps percentage is: " + apps_percentage + "%");
/* 
	Result:
	- hbcv: "Your apps percentage is: 12.0%" 
*/


// to get all of the data you've added as a list:
List<MyData> myDataList = getAllData();
int dataCount = myDataList.size(); // get a count of how much data you've added
Log.v("hbcv", "Data Count: You have a total of " + dataCount + ".");

// Itterate through all of the details on the list.
for(MyData myData: myDataList){
    Log.v("hbcv", "Id: " + myData.getId());
    Log.v("hbcv", "Percentage: " + myData.getPercentage());
}
/* 
	Result:
	- hbcv: "Count: You have added 2 details.";
	
	
	- hbcv: "Id: MEDIA_ID";
    - hbcv: "Percentage: 12.0%";
    
    - hbcv: "Id: APPS_ID";
    - hbcv: "Percentage: 35.0%";
*/
```

