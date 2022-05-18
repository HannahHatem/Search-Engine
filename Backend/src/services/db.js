const { Client } = require("pg");

const client = new Client({
  user: "postgres",
  password: "1234567890",
  host: "localhost",
  port: 5432,
  database: "search_engine",
});

exports.client = client;