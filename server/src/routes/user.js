const { Router } = require('express');
const { Types } = require('mongoose');
const crypto = require('crypto');
const { v4: uuidv4 } = require('uuid');

const router = Router();

const User = require('../models/User');
const Token = require('../models/Token');

const auth = (req, res, next) => {
        const authHeader = req.headers['authorization']
        const token = authHeader && authHeader.split(' ')[1]

        if (token == null) return res.sendStatus(401);

        Token.find({
                token: token
        }, (err, doc) => {
                console.log(doc);
                if(err != null) {
                        console.error(err);
                        res.json({
                                error: err
                        });
                        return;
                }
                if(!doc.length) {
                        res.sendStatus(401);
                        return;
                }
                console.log(doc);
                next();
        });
}

router.post('/register', (req, res) => {
	const username    = req.body.username;
	const password    = req.body.password;
	const max_score_f = 0;
	const max_score_m = 0;
	const max_score_d = 0;

	const password_hash = crypto.createHmac('sha256', password)
                   .update('AppOnGridTraineer')
                   .digest('hex');
	
	User.findOne({
		username: username
	}, (err, doc) => {
		if(err != null) {
			console.error(err);
			res.json({
				error: err
			});
			return;
		}
		if(doc != null) {
			res.json({
				error: "User already exists"
			});
			return;
		}
		const user = new User({
			username: username,
			password: password_hash,
			max_score_f: max_score_f,
			max_score_m: max_score_m,
			max_score_d: max_score_d
		});

		user.save((err, doc) => {
			if(err != null) {
				console.error(err);
				res.json({
					error: err
				});
				return;
			}
			res.json({
				success: doc
			});
		});
	});
});

router.post('/login', (req, res) => {
	const username = req.body.username;
	const password = req.body.password;

	const password_hash = crypto.createHmac('sha256', password)
                   .update('AppOnGridTraineer')
                   .digest('hex');

	User.findOne({
		username: username
	}, (err, doc) => {
		if(err != null) {
			console.error(err);
			res.json({
				error: err
			});
			return;
		}
		if(doc == null) {
			res.json({
				error: "Password or user doesn't match"
			});
			return;
		}
		console.log(doc);
		console.log(`${doc.password} === ${password_hash}`);
		if(doc.password === password_hash) {
			const token = new Token({
				token: uuidv4(),
				expiration: new Date(),
				user: doc._id
			});

			token.save((err, doc) => {
				if(err != null) {
					console.error(err);
					res.json({
						error: err
					});
					return;
				}
				setTimeout(() => {
					Token.findByIdAndDelete(doc._id, (err, doc) => {
						if(err != null){
							console.error(err);
							return;
						}
						console.log(`Token ${doc.token} deletado.`);
					});
				}, 3600000);
				res.json({
					success: {
						token: doc.token
					}
				})
			});
		} else {
			res.json({
				error: "Password or user doesn't match"
			});
		}
	});
});

router.post('/set/score', auth, (req, res) => {
	const authHeader = req.headers['authorization']
        const token = authHeader && authHeader.split(' ')[1]

	console.log(req.body);

	const max_score_f = req.body.max_score_f ? Number(req.body.max_score_f) : -1;
	const max_score_m = req.body.max_score_m ? Number(req.body.max_score_m) : -1;
	const max_score_d = req.body.max_score_d ? Number(req.body.max_score_d) : -1;

	const mongo_set = {
		$set: {}
	}

	if(max_score_f == -2)
		mongo_set.$set.max_score_f = 0;
	else if(max_score_f > -1)
		mongo_set.$set.max_score_f = max_score_f;
	
	if(max_score_m == -2)
		mongo_set.$set.max_score_m = 0;
	else if(max_score_m > -1)
		mongo_set.$set.max_score_m = max_score_m;
	
	if(max_score_d == -2)
		mongo_set.$set.max_score_d = 0;
	else if(max_score_d > -1)
		mongo_set.$set.max_score_d = max_score_d;

	console.log(mongo_set);

	Token.findOne({
		token: token
	}, (err, doc) => {
		if(err != null) {
			console.error(err);
			res.json({
				error: err
			});
			return;
		}
		if(doc == null) {
			res.json({
				error: "Token not found"
			});
			return;
		}
		const user_id = doc.user;
		User.findOneAndUpdate({
			_id: user_id
		}, mongo_set, (err, doc) => {
			if(err != null) {
				console.error(err);
				res.json({
					error: err
				});
				return;
			}
			console.log(`Dados do usuário ${doc.username} alterados com sucesso`);
			res.json({
				success: "Dados alterados com sucesso"
			});
		});
	});
});

router.get('/get/score', auth, (req, res) => {
	const authHeader = req.headers['authorization'];
        const token = authHeader && authHeader.split(' ')[1];

	Token.findOne({
		token: token
	}, (err, doc) => {
		if(err != null) {
			console.error(err);
			res.json({
				error: err
			});
			return;
		}
		if(doc == null) {
                        res.json({
                                error: "Token not found"
                        });
                        return;
                }
		User.findById(doc.user, (err, doc) => {
			if(err != null) {
				console.error(err);
				res.json({
					error: err
				});
				return;
			}
			if(doc == null) {
				res.json({
					error: "Token user not found"
				});
				return;
			}
			const max_score_f = doc.max_score_f;
			const max_score_m = doc.max_score_m;
			const max_score_d = doc.max_score_d;

			res.json({
				success: {
					max_score_f: max_score_f,
					max_score_m: max_score_m,
					max_score_d: max_score_d
				}
			});
		});
	});
});

router.get('/kca7fhghzagiz', (_, res) => {
    res.sendFile('html/registrar.html', {
        root: __dirname
    });
});

router.get('/kca7fhghzagia', (_, res) => {
    res.sendFile('html/login.html', {
        root: __dirname
    });
});

module.exports = router;
