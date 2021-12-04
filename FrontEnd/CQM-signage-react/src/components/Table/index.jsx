import { Table, TD, TH, TR, Wrapper } from "./Table.styles";
import { useState } from "react";

const DisplayTable = (props) => {
  const [dummyTableData, setDummyTableData] = useState(Array(10));

  return (
    <Wrapper>
      <Table>
        <TR>
          <TH>Token</TH>
          <TH>Counter</TH>
        </TR>
        {props.tableData &&
          props.tableData.map((m) => {
            return (
              <TR>
                <TD>{m.tokenNumber}</TD>
                <TD>{m.counterNumber}</TD>
              </TR>
            );
          })}
      </Table>
    </Wrapper>
  );
};

export default DisplayTable;
