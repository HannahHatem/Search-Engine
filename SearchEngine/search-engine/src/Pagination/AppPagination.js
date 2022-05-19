import React, { useEffect, useState } from "react";
// import { Box,Pagination } from "@material-ui/core";

import Box from "@mui/material/Box";
import Pagination from "@mui/material/Pagination";

const pageSize = 20;
function AppPagination(props) {
  const [pagination, setPagination] = useState({
    count: 0,
    from: 0,
    to: pageSize,
  });
   useEffect(() => {
     setPagination({ ...pagination, count: props.documents.length });
      props.setResults(props.documents.slice(pagination.from, pagination.to))
   }, [pagination.from,pagination.to]);

  console.log(
    "paginated",
    props.documents.slice(pagination.from, pagination.to)
  );

  const handlePageChange=(event, value)=>{
       const from=(value-1)*pageSize;
         const to=value*pageSize;
         setPagination({ ...pagination, from: from,to:to });
  }
  return (
    <Box
      justifyContent={"center"}
      alignItems={"center"}
      display={"flex"}
      sx={{ margin: "20px 0px" }}
    >
      <Pagination count={Math.ceil(pagination.count / pageSize)} onChange={handlePageChange} />
    </Box>
  );
}

export default AppPagination;
