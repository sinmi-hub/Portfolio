**Project Goals**

For this project, I built a message generator program. Every time a user runs the program, they get a new, randomized output. The messages can either be an astrology message generator, inspirational message, or nonsensical jokes or Christian quotes depending on the user. This program will output 3 different messages depending on what category of message that user chooses. If the category of message is not stipulated, then it will randomly choose from the 5 categories listed above.

**Object include**\
messages: This object is in messageGenerator.js and it is what makes up the program

**Methods include**\
_addMessage(messagetype, message)_: This method adds a message to the current object depending on what type/ category that the message is. This message is then stored in the current object to be part of possible messages to randomly generate for user\

_generateRandMsg(message)_: This method generates a random message from all possible messages stored in current object and then logs this message back to user. The logic behind this method is to randomly generate a number within the bounds of the amount of messages stored, and use that number as an index to choose from array.