<p align="center">
    <img src="images\softrf_conf.png" width="120px"/>
</p>

<p align="center">
    <img src="images\ss1.jpg" width="240px"/>
    <img src="images\ss2.jpg" width="240px"/>
</p>

# What is it
This is a very simple android configuration software for [SoftRF devices](https://github.com/lyusupov/SoftRF)<br>
There is an app for that already, but there is no source code available. The app is outdated, it doesn't have newer protocols and the BLE search results were always bloated with some random devices. My app fixes those issues, although there are only base setting available for now.<br><br>
As I don't know how to code for android I found a visual programming tool called [APP Inventor](https://ai2.appinventor.mit.edu/) (AI) and this software is built using that.
# How to build
There are two parts to this app. One is an AI project file - SoftRF_configurator.aia. You simply import that and you are ready to modify the software. When you are done you click "Build" in the AI top menu.<br>
Second part is a custom extension (extentions\\softrf) implementing some data processing code that I wasn't able to achieve with the AI itself. The extension is already built and included in the project. But if you want to modify the extension you have to implement changes, build the extension and import it again into the AI. The extension is built using [FAST](https://github.com/jewelshkjony/fast-cli).