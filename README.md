# TwitchBot
TwitchBot

# About the Project
This project has the goal to create a twitchbot which is able to manage and administrate the twitchchat by his own.
In addition you should be able to operate with the bot and control it with actions and doings which can interact with the bot and the chat.

To Control the Bot there should be a Open REST-API and a simple webinterface which gives you access to the bot to interact with it.

Everyone is welcome do do pull requests or improve or support this project with whatever you want. If you got some questions, you are welcome to ask, you can reach me via the mail added to my profile (fo0).

# Example
```
java -jar twitchbot.java -c -v -a "templates/botactions.json" -f "templates/faq.json" -k "testkey"
```

# Usage
```
Usage: java -jar twitchbot.jar <OPTIONS>

Options category 'Api':
  --[no]commandLineApi [-c] (a boolean; default: "false")
    Commandline Api

Options category 'DEBUG':
  --[no]debug [-v] (a boolean; default: "false")
    Debugging

Options category 'GUI':
  --[no]nogui [-n] (a boolean; default: "false")
    Graphical User Interface

Options category 'REST':
  --apiKey [-k] (a string; default: "")
    REST ApiKey
  --port [-p] (an integer; default: "1234")
    REST Api Port

Options category 'Template':
  --actiontemplate [-a] (a string; default: "")
    Action
  --faqtemplate [-f] (a string; default: "")
    FAQ
  --variabletemplate [-r] (a string; default: "")
    Action
```
# TODO
## working on current issues
* Spring integration


## bugs
* fix bug that it sometimes spam all the joined people in the channel

## enhancements
* add log4j2 (but without logfile)
* creating templates for: variables
* bot startup message not hard-coded

## features
* Web GUI
* REST Server / Api
* call a website and parse the content via regex
* too much the same question -> create automatically a action
* idle detect of user (do defined actions)
* private message to join user with welcome message
* private message to join user with help commands
* Custom Command Variables:: added via templates
* history of chat inputs (for faster processing it to the templates)
* raffle (giveaway) module
* receive private messages
* music controller (!music vote youtube.link)
* cmd api -> private message -> list of existing bots when asking what bot should be taken
* private message after actions like followed by someone and so on
* spam detection module with custom actions like if some words comes too often (not from one account, from the chat) then change the chat-mode to only subscriber


## finished
* refactoring configs
* added full template support including all (also internal) commands
* FAQ automatic answer questions -> finished but missing template integration
* distance algorithmn like ssdeep/jaro winker... to add a matching score when the bot should take a question/answer
* user join message (testen, still broken? not!)
* multiple bot creation
* custom action templates
* simple spam detection
* commandline api
* 	add bots
* start params (options)
* version update functionality (updater)
* save twitchbots sessions to load config/json
* Spam detection :: first parameter based detection finished
* BotAction Api / Templates :: first iteration finished 

## ON HOLD =
* GoogleTranslate :: costs money! <br>

--------------------------------------------------------------------------

# development requirements
* project lombok: https://projectlombok.org/

# usage requirements
## oauth key twitch
* https://twitchapps.com/tmi/
* save to path (in folder of java app): "auth/oauth.key"

# External Libraries
https://github.com/KittehOrg/KittehIRCClientLib
