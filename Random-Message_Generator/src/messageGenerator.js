/*
Author: Sinmi
Date: 05/29/2023
*/

/*This program  is message generator program. Every time the program is run, it generates a randomized output in message. It either randomly generates based on category such as 'motivational', 'astrology', 'dadJokes', 'jokes', 'christian' if desired category is given, or else it would randomly generate from all 5 categories. The structure of the program is such that the message generator is implemented as an object with methods. Each category is pre-written with messages and is randomly choosen and given back to user */

const messages = (username) => {
    let msgObject = {
        _username: username,

        /*Category of possible jokes that could be used */
        allCategory : ['motivational', 'astrology', 'dadJokes', 'jokes', 'christian'],

        motivational: [
            "Believe in yourself and all that you are. Know that there is something inside you that is greater than any obstacle.",
            "The only limit to our realization of tomorrow will be our doubts of today.",
            "The future belongs to those who believe in the beauty of their dreams.",
            "The only way to achieve the impossible is to believe it is possible.",
            "Don't watch the clock; do what it does. Keep going.",
            "Believe you can and you're halfway there.",
            "The harder you work for something, the greater you'll feel when you achieve it.",
            "Don't stop when you're tired. Stop when you're done.",
            "Dream it. Believe it. Build it.",
            "Difficult roads often lead to beautiful destinations."
        ],

        astrology: [
            "As a Gemini, you're known for your quick wit and lively personality.",
            "Scorpios are known for their passion and intensity.",
            "As a Leo, you're a natural born leader and love to be in the spotlight.",
            "Cancers are known for their compassion and caring nature.",
            "Virgos are known for their attention to detail and hard-working nature.",
            "As a Libra, you're all about balance and harmony in all things.",
            "Aquarius individuals are known for their originality and independence.",
            "Taurus, you are known for being reliable, practical, ambitious and sensual.",
            "Aries, you are enthusiastic, optimistic, and passionate.",
            "Pisces, you are known for your compassionate and empathetic nature"
        ],

        christian: [
            "God is our refuge and strength, a very present help in trouble.",
            "I can do all things through Christ who strengthens me.",
            "For God so loved the world that He gave His one and only Son.",
            "Trust in the LORD with all your heart, and do not lean on your own understanding.",
            "We walk by faith, not by sight.",
            "For I know the plans I have for you, declares the LORD, plans to prosper you and not to harm you, plans to give you hope and a future.",
            "Be strong and courageous. Do not be afraid; do not be discouraged, for the LORD your God will be with you wherever you go.",
            "But those who hope in the LORD will renew their strength. They will soar on wings like eagles; they will run and not grow weary, they will walk and not be faint.",
            "And we know that in all things God works for the good of those who love him, who have been called according to his purpose.",
            "The LORD is my light and my salvation—whom shall I fear? The LORD is the stronghold of my life—of whom shall I be afraid?"
        ],

        jokes: [
            "Why don't some fish play piano? Because you can't tuna fish!",
            "What did the grape say when it got stepped on? Nothing, it just let out a little wine.",
            "Why was the math book sad? Because it had too many problems.",
            "Why did the scarecrow win an award? Because he was outstanding in his field!",
            "Why don't we write with a broken pencil? Because it’s pointless.",
            "Why was the computer cold at the office? It left its Windows open!",
            "How do you organize a space party? You planet."
        ],

        dadJokes: [
            "Why don't scientists trust atoms? Because they make up everything!",
            "Why did the bicycle fall over? Because it was two-tired!",
            "Why can't you give Elsa a balloon? Because she will let it go.",
            "I would tell a joke about pizza, but it's a little cheesy.",
            "Did you hear about the guy who invented Lifesavers? He made a mint!",
            "Why don't skeletons fight each other? They don't have the guts.",
            "Why couldn't the bicycle stand up by itself? It was two tired.",
            "What do you call cheese that isn't yours? Nacho Cheese!",
            "I'm reading a book about anti-gravity. It's impossible to put down!",
            "Why did the golfer bring two pairs of pants? In case he got a hole in one.",
            "Why don't eggs tell each other jokes? They can crack up!",
             "Why couldn't the leopard play hide and seek? Because he was always spotted.",
            "Why did the tomato turn red? Because it saw the salad dressing!"
        ],

        /*Getter and Setter method for variables */
        get username() {
            return this._username;
        },

        set username(name) {
            this._username = name; 
        },
        
        /* This method adds message to the current object depending on what type and category that the message belongs to. It checks to make sure  that the messages are valid before adding them.*/
        addMessage(messagetype, message) {
            
            /* Checking for valid parameters. The message type or category of message must be given and the message must not be empty */
            if (typeof (message) !== 'undefined' && message !== '' &&
                typeof (messagetype) !== 'undefined' &&messagetype !== '') 
            {

                /* Checks that user entered a valid category */
                if (this.allCategory.includes(messagetype)) {
                    /*Using switch statements to handle different cases depending on what messagetype is */
                    switch (messagetype) {
                        case 'motivational': this.motivational.push(message);
                            console.log('Added message successfully');
                            break;
                    
                        case 'astrology': this.astrology.push(message);
                            console.log('Added message successfully');
                            break;
                    
                        case 'christian': this.christian.push(message);
                            console.log('Added message successfully');
                            break;
                    
                        case 'jokes': this.jokes.push(message);
                            console.log('Added message successfully');
                            break;
                                        
                        case 'dadJokes': this.dadJokes.push(message);
                            console.log('Added message successfully');
                            break;
                    }
                }

                else
                    console.log('Invalid category. Please choose the' + 
                            'category you would like to add your message to')
            }

            /*If invalid, then it echoes error, and tells user to try again */
            else
                console.log('Message cannot be empty');
        },

        /*This method randomly generates a number and uses that number as an index to choose a message depending on the type of message requested. The method then returns the string at that index. If the parameter,
        'messagetype' is not given, it will randomly generates from all 5 catgories available*/
        generateRandMsg(messagetype) {
            let randMsg = '';

            /*Generates a random integer between 0 and 9. We do not include 10
            because accessing index 10 would be error, since there are exactly 10 messages*/
            let index = Math.floor(Math.random() * 9);

            /* Checks to make sure that the messagetype is a valid category */
            if (messagetype !== 'undefined' && messagetype !== ''
                && this.allCategory.includes(messagetype))
            {
                console.log(`Here is one generated just for you ${this._username}!`);
                
                /*returning the random message */
                /*Using switch statements to handle different cases depending on what messagetype is */
                switch (messagetype) {
                    case 'motivational': randMsg = this.motivational[index];
                        console.log('Motivational message of the day:');
                        break;
                
                    case 'astrology': randMsg = this.astrology[index];
                        console.log('Astrology message of the day:');
                        break;
                
                    case 'christian': randMsg = this.christian[index];
                        console.log('Spiritual message of the day:');
                        break;
                
                    case 'jokes': randMsg = this.jokes[index];
                        console.log('Joke of the day:');
                        break;
                                    
                    case 'dadJokes': randMsg = this.dadJokes[index];
                        console.log('Dad joke of the day:');
                        break;
                }
            }
            
            else {

                /*Merges all messages that are available */
                const allArray = this.christian.concat(this.dadJokes,
                    this.jokes, this.astrology, this.motivational);
                
                /* Generates a random index based on all sizes of categories available */
                index = Math.floor(Math.random() *
                    (this.christian.length + this.dadJokes.length + this.jokes.length + this.motivational.length + this.astrology.length));
                
                console.log(`Here is one generated just for you ${this._username}!`);
                randMsg = allArray[index];
            }

            return randMsg + '\n';
        }
    }

    return msgObject;
}

module.exports = messages;


