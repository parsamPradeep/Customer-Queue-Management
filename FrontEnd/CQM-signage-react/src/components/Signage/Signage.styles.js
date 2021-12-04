import styled from "styled-components";

export const Wrapper = styled.div`
  display: grid;
  grid-template: 14vh 10vh 58vh 14vh / 40fr 60fr;

  .box-one {
    grid-column: 1/ -1;
    height: 15vh;
    justify-content: center;
    align-content: center:
  }
  .box-last {
    grid-column: 1 / span 2;
    grid-row: 4;
    height: 15vh;
    justify-content: center;
    align-content: center:
  }
  .box-table {
    grid-row: 2 / span 2;
    justify-content: center;
    align-content: center:
  }
`;
