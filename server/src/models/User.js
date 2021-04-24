const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
	username: {
		type: String,
		required: true,
		unique: true,
		dropDups: true
	},
	password: {
		type: String,
		require: true
	},
	max_score_f: {
		type: Number,
		default: 0
	},
	max_score_m: {
		type: Number,
		default: 0
	},
	max_score_d: {
		type: Number,
		default: 0
	}
});

const User = mongoose.model('User', userSchema);

module.exports = User;
