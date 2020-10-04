# SmsCatcher

> This project is created for being used as a part of the project FaucNet.
> FaucNet is a Python 3.8.5 Project. (currently private)
> SmsCatcher needs >=Android KITKAT version to work.

> SmsCatcher needs to be killed at least once on the device to work smoothly.
> You can open and then kill it. (Hold on the menu button and then swipe left/right the application)

> After killing and restarting once, you can start using the SmsCatcher.

> Currently, it has two buttons.

> One of them activates a service that is going to run even if you kill the application and the other stops it.
> The service is then reading the last 3 SMS on your inbox and sends them to the specified mail account for automation purposes.
> Unless its service gets stopped (which it can get via stop services button), it will continue to send the latest 3 SMS per 20 mins.
> It uses JavaMail API to send the mail.
> You have to have a gmail account to send mail.

- <a href="https://github.com/squasar/SmsCatcher/blob/master/app/src/main/java/devop/automation/smscatcher/email/WrittenMail.java" target="_blank">`WrittenMail.java`</a> : You can edit your mail addresses and passwords in this file.
