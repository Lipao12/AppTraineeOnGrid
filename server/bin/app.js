#!/usr/bin/env node

const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const https = require('https');
const fs = require('fs');

const app = express();

//app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

const router = require('../src/routes');

app.use('/', router);

const Token = require('../src/models/Token');

mongoose.connect('mongodb://127.0.0.1/AppOngridTraineer', {useNewUrlParser: true, useUnifiedTopology: true, useFindAndModify: false}, err => {
    if(err != null) {
        console.error(err);
        return;
    }
    Token.deleteMany({}, (err) => {
	if(err != null) {
		console.error(err);
		process.exit(1);
	}
	console.log("Todos os tokens de acesso foram deletados ao iniciar o sistema.");
    });
    const PORT = process.env.PORT ? Number(process.env.PORT): 8443;
    const httpsServer = https.createServer({
        key: fs.readFileSync('/root/SSL/server.key'),
        cert: fs.readFileSync('/root/SSL/server.crt'),
    }, app);
    httpsServer.listen(PORT, () => {
	console.log(`Listening at port ${PORT}`);
    });
});
