const mongoose = require('mongoose');

const questionSchema = new mongoose.Schema({
    text:       {
        type: String,
        required: true
    },
    correct:    {
        type: String,
        required: true
    },
    incorrect1: {
        type: String,
        required: true
    },
    incorrect2: {
        type: String,
        required: true
    },
    difficulty: {
        type: String,
        minLength: 1,
        maxLength: 1
    },
    curiosity:  {
        type: String,
        required: true
    }
});

const Question = mongoose.model('Question', questionSchema);

module.exports = Question;