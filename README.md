# Top5 Benchmark Automation Testing

## Introduction

Being a part of the software development life cycle, Benchmark Testing is a very useful tool for determining current performance but also highlights where improvement is necessary.
Current Quantum Release success won’t have been possible without notable increase of speed on PageLoading in comparison with the main contender, Google Chrome. So Benchmark testing is crucial in tracking Firefox performance progress.

It all began in MAY 2017 when we received the first requests on Benchmark testing for THE top five websites: Amazon, Facebook, Google Search, Gmail and Youtube. At that point we done it between Firefox and Google Chrome and later between Firefox browsers with specific preferences like WebRender or OMTP.
The manual approach consists in using a Delgado Game Capture device to record all the scenarios at 60 FPS performed on an Acer machine having a Real time mouse/key monitor opened to spot clicks and keys tap events. For analysis we used QuickTime frame viewer to spot the frames for FirstPaint, Hero elements and LastPaint for each step of the scenario.

This process implies quite an effort considering that you need to provide weekly results for all Top 5 websites on several different browser settings.
The need here for Automation was obvious. We started looking for solutions and finally we put the base for this Java Maven based project.

Java offers good support for image comparison (Sikuli library). Sikuli can recognize a key part from an image using a defined region. In our framework we are using the Sikuli algorithm of image patterns in order to compare them with the frames.

We record the flows, generate the video and split it into frames using the FFMPEG tool.
FFMPEG is a very strong command line program which can be used for recording, converting and splitting a video using various parameters as command (video bitrate, compressing speed, video library that the user wants tot use etc).
Also to make a screen record we need to use an extra tool called screen-capture-recorder. Basically this tool takes multiple screenshots and send them to the FFMPEG in order to be processed. This tool works like a virtual camera.

## Installation

Currently Top5 Benchmark Automation Testing Framework is supported only on Windows

1. Clone the repository on your machine
2. Create a new Firefox Profile (log into your Google or Facebook accounts if you wish to run those scenarios). Be sure to have your Firefox Window maximized before closing the browser
3. Download Screen Capture Recorder Exe file from https://github.com/rdp/screen-capture-recorder-to-video-windows-free/releases and install it on your machine
4. Download FFMPEG from https://ffmpeg.zeranoe.com/builds/ extract it on your machine and add the \bin folder as Environment System variable under Path section
5. Navigate to C:\Users\username\.m2\repository\org\seleniumhq\selenium\selenium-firefox-driver\3.6.0 -> open archive selenium-firefox-driver-3.6.0.jar -> Edit org\openqa\selenium\firefox\webdriver_prefs.json -> change value for "browser.link.open_newwindow" from 2 to 3 (this setting was frozen by selenium until better support will be available. This is the only way to change the setting for now)

## Configuration

Application can be configured from config.properties file located in the project's \resources folder

* **profile_path** option should point to the location of the Firefox Profile previously created
* **scenarios_to_execute** option contains the list of scenarios you wish to execute (ex: amazon,facebook,google,gmail,youtube)
* **number_of_runs** option represents the number of times a scenario is executed
* **default_similarity** option is the value Sikuli uses as similarity parameter for pattern searches. The highest number for this option is **1.00** but will slow down the time in which a search is performed
* if you wish to receive email notifications with results set **email_notification** option to **true**. Please also set up values for **email_sender** (account that sends the email), **error_email_recipients** (list of recipients that will receive an email in case of an error), **results_email_recipients** (list of recipients that will receive the results), **email_user_name** and **email_password** (login information for the account that sends the email notifications. WARNING!!! Keep this information safe, don't commit any changes with this information filled). Currently only gmail is supported as email sender

## Execute run

Run the **main()** method from the Runner Class ( ...\Top5_Benchmark\src\test\java\org\mozilla\benchmark\Runner.java)




