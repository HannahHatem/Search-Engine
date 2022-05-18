const { client } = require("./db");
const natural = require("natural");

const queryProcessing = async (searchWord) => {
  const stemmer = natural.PorterStemmer;
  const stemmedWords = stemmer.tokenizeAndStem(searchWord);

  const query = `SELECT id FROM word_id WHERE word IN (${stemmedWords
    .map((word) => `'${word}'`)
    .join(",")})`;

  const wordIds = await client
    .query(query)
    .then((result) => {
      const rows = result.rows;
      const wordIds = rows.map((row) => row.id);
      return wordIds;
    })
    .catch((err) => {
      throw err;
    });

  const query2 = `SELECT * FROM word_indexer WHERE word_id IN (${wordIds
    .map((id) => `'${id}'`)
    .join(",")})`;

  const wordIndexes = await client
    .query(query2)
    .then((result) => {
      return result.rows;
    })
    .catch((err) => {
      throw err;
    });

  const wordIndexesGrouped = wordIndexes.reduce((group, index) => {
    const { url_id } = index;
    group[url_id] = group[url_id] || [];
    group[url_id].push(index);
    return group;
  }, {});

  return wordIndexesGrouped;
};

module.exports = queryProcessing;