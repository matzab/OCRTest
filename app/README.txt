
Introduction:
The purpose of this application is to test the performance of tess-two OCR library.
 Tess-two is a fork of tesseract-android-tools (set of Android APIs for Tesseract OCR engine)
 The library works with Tesseract 3.05, Leptonica 1.74.1 (image processing library), libjpeg 9b
 abd libpng 1.6.25.

 Tesseract 3.05 behind the scenes:
 ...
 ...
 ...

Initializing tess-two library:
1. adding dependency to gradel.build file:
dependencies {
    implementation 'com.rmtheis:tess-two:9.1.0'
}

2. defining the data folder (called tessdata)
 where we copy trained data for the language we are going to process (english
 in our case)

 We created tessdata folder in a devices' external storage and put in the
 .traineddata file


Tests are performed on modern smartphone (HUAWEI P30).

P30 Specs:
Operating system: Android 10
...
...
...

Test prerequisites:
 - sample images stored in devices' external storage
 - timer to measure duration of processing of an each individual image
 - timer to measure overall duration of the testing
 - writing results to the log folder
 - generating trace files (for CPU usage)
 - generating memory dumps for memory usage
 - generating battery stats report to track battery usage


Measured parameters:
 - CPU performance - % - min/max; average over time
 - Time - total time for 12,189 images scanned
 - Accuracy - sample of 100 out of 12,189 images manually checked for accuracy; extrapolate findings
 - Space - RAM used - min/max; average over
 - Power usage - power consumed - total over 12,189 images scanned

Tesseract 3.05 testing:
- 3 tests need to be performed:
 i.  1st test involves profiling every parameter (phone connected to pc by USB cable) except battery.
 ii. 2nd test we test only power consumption, where phone is not connected to the power source
 iii. 3rd test, we measure speed of image processing


  Performing 1st test:
1. run the app (apk), phone connected to PC ny USB cable
2. start android profiler
3. wait for the test to finish
4. copy the test results to pc, command: adb pull ...
5. analyze the data

 Performing 2nd test:
 1. reset battery stats, command: adb shell dumpsys batterysts --reset
 2. dump battery data onto pc: adb shell dumpsys batterystats > {path}/batterystats.txt
 3. run docker image for battery historian, command: docker run -p {port}:9999 gcr.io/android-battery-historian:2.1 --port 9999
 4. open battery historian in browser, URL: localhost:7676
 5. analyze the data

 Performing 3rd test
 1. run the app (apk)
 2. do not start android profiler and do not run debug operations (generating trace file and heap dump)
 3. wait for the test to finish
 4. copy the test results to pc, command: adb pull ...
 5. analyze the data

