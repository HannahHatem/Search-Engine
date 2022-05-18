const express = require("express");
const bodyParser = require("body-parser");
const { pool, client } = require("./db");
const natural = require("natural");
const router = express.Router();
const queryProcessing = require("./queryProcessing");
const ranker = require("./ranker");
const readData = require("./middleware")

router.get("/search", readData, async (req, res) => {
  try {
    const searchWord = req.query.q;
    const documentsInfo = await queryProcessing(searchWord);
    return res.status(200).send({ documents: await ranker(documentsInfo, req.documentsCount, req.wordDocumentCount) });
  } catch (err) {
    return res.status(500).send({ error: err.status });
  }
});

module.exports = router;
