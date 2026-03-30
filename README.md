# SmsCatcher

An Android utility app that periodically reads recent inbox SMS messages and forwards them by email.

> Originally created as a supporting component for **FaucNet** (private project).

## Overview

**SmsCatcher** is a lightweight Android background-service project built for simple SMS-to-email forwarding automation.

Its main purpose is straightforward:

- read the latest inbox SMS messages on the device
- build a plain-text email payload from those messages
- send them to a configured email address
- repeat the process on a timed interval in the background

This repository is best understood as a **focused automation prototype / utility app**, not a production-ready mobile product.

---

## What it does

When the service is active, SmsCatcher:

1. reads the latest SMS messages from the device inbox
2. selects the most recent messages
3. formats message details into an email body
4. sends that email using a configured Gmail account
5. repeats the process periodically

The current implementation is designed around forwarding the **latest 3 inbox SMS messages** on a **20-minute interval**.

---

## How it works

The project is organized around a small set of responsibilities:

- **MainActivity**  
  Starts and stops the background service.

- **SendingService**  
  Runs in the background and re-triggers the SMS processing flow on a delay cycle.

- **Process**  
  Coordinates the main logic:
  - read SMS
  - build email content
  - send email

- **SmsReader / Sms**  
  Reads messages from Android’s SMS content provider and maps them into simple objects.

- **WrittenMail / SendMail**  
  Handles email composition and delivery through JavaMail.

---

## Tech Stack

- **Java**
- **Android**
- **Android background service**
- **JavaMail**
- **Telephony SMS content provider**

---

## Requirements

- Android device with **KitKat (API 19) or newer**
- SMS inbox available on the device
- Internet access
- A Gmail account for outgoing email delivery

---

## Permissions

The app uses the following Android permissions:

- `READ_SMS`
- `INTERNET`

Because this project reads private SMS content, it should only be used in environments where that access is fully intentional and authorized.

---

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/squasar/SmsCatcher.git
```


## Please Remember

> SmsCatcher needs to be killed at least once on the device to work smoothly.
> You can open and then kill it. (Hold on the menu button and then swipe left/right the application)

> After killing and restarting once, you can start using the SmsCatcher.

> Currently, it has two buttons.

> One of them activates a service that is going to run even if you kill the application. If pressed, the other button stops it.
> The service is reading the last 3 SMS on your inbox and sends them to the specified mail account for automation purposes.
> Unless its service gets stopped (which it can get via stop services button), it will continue to send the latest 3 SMS per 20 mins.
> It uses JavaMail API to send the mail.
> You have to have a gmail account to send mail.

- <a href="https://github.com/squasar/SmsCatcher/blob/master/app/src/main/java/devop/automation/smscatcher/email/WrittenMail.java" target="_blank">`WrittenMail.java`</a> : You can edit your mail addresses and passwords in this file.

