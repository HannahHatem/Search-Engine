const { client } = require("./db");
const {
  titleWeight,
  h1Weight,
  h2Weight,
  h3Weight,
  h4Weight,
  h5Weight,
  h6Weight,
  bodyWeight,
  graphWeight,
} = require("./../config");
const { user } = require("pg/lib/defaults");

const calculateScores = async (
  documentsInfo,
  documentsSize,
  wordDocumentSize
) => {
  try {
    const documentsCount = documentsSize;
    const wordDocumentCount = wordDocumentSize;
    const urlScores = {};
    const urlIds = Object.keys(documentsInfo);
    const webRank = await calculateRank(urlIds);

    for (let i = 0; i < urlIds.length; i++) {
      const urlId = urlIds[i];
      const urlinfos = documentsInfo[urlId];
      let relevance = 0;

      for (let i = 0; i < urlinfos.length; i++) {
        const info = urlinfos[i];
        const {
          title,
          h1,
          h2,
          h3,
          h4,
          h5,
          h6,
          body,
          count,
          word_id,
          doc_size,
        } = info;
        const titleRelevance = titleWeight * title;
        const h1Relevance = h1Weight * h1;
        const h2Relevance = h2Weight * h2;
        const h3Relevance = h3Weight * h3;
        const h4Relevance = h4Weight * h4;
        const h5Relevance = h5Weight * h5;
        const h6Relevance = h6Weight * h6;
        const bodyRelevance = bodyWeight * body;
        const TF_IDF =
          (count / doc_size) * (documentsCount / wordDocumentCount[word_id]);

        const totalRelevance =
          titleRelevance +
          h1Relevance +
          h2Relevance +
          h3Relevance +
          h4Relevance +
          h5Relevance +
          h6Relevance +
          bodyRelevance +
          TF_IDF;
        relevance += totalRelevance;
      }
      urlScores[urlId] =  relevance + webRank[urlId];
    }
    //sort urlScores by descending order according to score
    const sortedUrlScores = Object.keys(urlScores)
      .sort((a, b) => urlScores[b] - urlScores[a])
      .map((urlId) => {
        return {
          urlId,
          score: urlScores[urlId],
        };
      });
      console.log(sortedUrlScores);
    return sortedUrlScores;
  } catch (err) {
    throw err;
  }
};

const calculateRank = async (urlIds) => {
  const webGraph = {};
  for (let i = 0; i < urlIds.length; i++) {
    const urlId = urlIds[i];
    const query = `SELECT parent_url_id FROM popularity WHERE url_id = '${urlId}'`;
    await client.query(query).then((result) => {
      const urlParents = result.rows.map((row) => {
        return row.parent_url_id;
      });
      webGraph[urlId] = urlParents.length * graphWeight;
    });
  }
  return webGraph;
};

module.exports = calculateScores;
