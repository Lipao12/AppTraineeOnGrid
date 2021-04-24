const { Router } = require('express');
const jwt = require('jsonwebtoken');

const router = Router();

const questionRoute = require('./routes/question');
const userRoute = require('./routes/user');

router.use('/question', questionRoute);
router.use('/user', userRoute);

module.exports = router;
