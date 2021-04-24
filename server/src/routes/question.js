const { Router } = require('express');
const { Types } = require('mongoose');

const router = Router();

const Question = require('../models/Question');
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

router.get('/kca7fhghxagiz', (_, res) => {
    res.sendFile('html/inserir.html', {
        root: __dirname
    });
});

router.get('/kca7fhghzagiz', (_, res) => {
    res.sendFile('html/deletar.html', {
        root: __dirname
    });
});

router.get('/list/:difficulty', auth, (req, res) => {
    const difficulty = req.params.difficulty;

    Question.find({
        difficulty: difficulty
    }, (err, docs) => {
        if(err != null) {
            console.error(err);
            res.json({
                error: err
            });
            return;
        }
        res.json({
            success: docs
        });
    })
});

router.post('/save', (req, res) => {
    const text       = req.body.text;
    const correct    = req.body.correct;
    const incorrect1 = req.body.incorrect1;
    const incorrect2 = req.body.incorrect2;
    const difficulty = req.body.difficulty;
    const curiosity  = req.body.curiosity;

    const question = new Question({
        text:       text,
        correct:    correct,
        incorrect1: incorrect1,
        incorrect2: incorrect2,
        difficulty: difficulty,
        curiosity:  curiosity
    });

    question.save((err, doc) => {
        if(err != null) {
            console.error(err);
            res.json({
                error: err
            });
            return;
        }
        console.log(`Documento ${doc._id} salvo com sucesso.`);
        res.json({
            success: 'Documento salvo com sucesso.',
            id: doc._id
        });
    });
});

router.post('/delete', (req, res) => {
    const id = Types.ObjectId(req.body.id);
    Question.findByIdAndDelete(id, (err, doc) => {
        if(err != null) {
            console.error(err);
            res.json({
                error: err
            });
            return;
        }
        if(doc == null) {
            res.json({
                error: `Documento ${req.body.id} não existe.`
            });
            return;
        }
        res.json({
            success: 'Documento deletado com sucesso.',
            doc: doc
        });
    })
});

module.exports = router;
