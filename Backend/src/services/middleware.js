const express = require("express");
const { client } = require("./db");

const getDocumentsCount = async () => {
  const query = `SELECT COUNT(*) FROM crawled_links`;
  const result = await client.query(query);
  return result.rows[0].count;
};

const getWordDocumentCount = async () => {
  const query = `SELECT word_id, COUNT(*) FROM word_indexer GROUP BY word_id`;
  const result = await client.query(query);
  const object = {};
  result.rows.forEach((row) => {
    object[row.word_id] = row.count;
  });

  return object;
};

const readData = async( req, res, next) =>{
    const documentsCount = await getDocumentsCount();
    const wordDocumentCount = await getWordDocumentCount();
    req.documentsCount = documentsCount;
    req.wordDocumentCount = wordDocumentCount;
    next();
}

module.exports = readData;