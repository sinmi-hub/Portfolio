const messageGenerator = require('./messageGenerator');

let user = 'John'
let generator = messageGenerator(user); // creating new instance

console.log(generator.generateRandMsg());
console.log(generator.generateRandMsg('christian'));
console.log(generator.generateRandMsg('jokes'));
console.log(generator.generateRandMsg('dadJokes'));
console.log(generator.generateRandMsg('astrology'));
console.log(generator.generateRandMsg('motivational'));