const mongoose = require('mongoose');
const { v4: uuidv4 } = require('uuid');

const tokenSchema = new mongoose.Schema({
	token: {
		required: true,
		type: String,
		default: () => uuidv4()
	},
	expiration: {
		require: true,
		type: Date,
		default: () => new Date()
	},
	user: {
		required: true,
		type: mongoose.ObjectId
	}
});

const Token = mongoose.model('Token', tokenSchema);

module.exports = Token;
