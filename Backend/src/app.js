const express = require("express");
const cors = require("cors");
const { pool, client } = require("./services/db");
const routes = require("./services/router");

const port = process.env.PORT || 5000;
const app = express();
app.use(express.json());
app.use(cors());

app.use(routes);


app.listen(port, () => {
    client
  .connect()
  .then(() => {
    console.log("Connected to database");
  })
  .catch((err) => {
    console.log("Error connecting to database", err.stack);
    process.exit(1);
  });
});
