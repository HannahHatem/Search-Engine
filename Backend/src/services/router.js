const express = require("express");
const bodyParser = require("body-parser");
const { pool, client } = require("./db");
const natural = require("natural");
const router = express.Router();
const queryProcessing = require("./queryProcessing");
const ranker = require("./ranker");
const readData = require("./middleware");
const { getLinkPreview } = require("link-preview-js");

router.get("/search/:page/:count", readData, async (req, res) => {
  try {
    const searchWord = req.query.q;
    const documentsInfo = await queryProcessing(searchWord);
    const sortedDocs = await ranker(
      documentsInfo,
      req.documentsCount,
      req.wordDocumentCount
    );

    const page = req.params.page != null ? req.params.page : 1;
    const count = req.params.count != null ? req.params.count : 5;

    const start = (page - 1) * count;
    const end = page * count;
    const slicedDocs = sortedDocs.slice(start, end);

    const urls = slicedDocs.map((doc) => doc.urlId);

    const query = `SELECT * FROM crawled_links WHERE url_id IN (${urls
      .map((url) => `'${url}'`)
      .join(",")})`;

    const urlsInfo = await client
      .query(query)
      .then((result) => {
        return result.rows.map((row) => {
          return {
            url: row.url,
          };
        });
      })
      .catch((err) => {
        throw err;
      });

    const urlData = [];

    for (let i = 0; i < urlsInfo.length; i++) {
      const data = await getLinkPreview(urlsInfo[i].url);
      urlData.push({
        url: urlsInfo[i].url,
        title: data.title,
        description: data.description,
      });
    }

    return res.status(200).send({ data: urlData });
  } catch (err) {
    return res.status(500).send({ error: err.status });
  }
});

module.exports = router;
