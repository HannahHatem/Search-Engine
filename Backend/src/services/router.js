const express = require("express");
const bodyParser = require("body-parser");
const { pool, client } = require("./db");
const natural = require("natural");
const router = express.Router();
const queryProcessing = require("./queryProcessing");

router.get("/search", async (req, res) => {
  try {
    const searchWord = req.query.q;
    const stemmed = await queryProcessing(searchWord);
    return res.status(200).send({ stemmed: stemmed });
  } catch (err) {
    return res.status(500).send({ error: err });
  }
});

module.exports = router;
