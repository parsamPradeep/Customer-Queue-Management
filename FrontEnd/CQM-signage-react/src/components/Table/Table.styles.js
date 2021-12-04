import styled from "styled-components";

export const Wrapper = styled.div`
  border: 3px solid grey;
  width: 40vw;
  height: 67vh;
  position: sticky;
  overflow-y: scroll;
  scrollbar-width: none;
  ::-webkit-scrollbar {
    display: none;
  }
`;

export const Table = styled.table`
  table-layout: auto;
  display: block;
  height: 67vh;
  border-collapse: collapse;
`;

export const TH = styled.th`
  text-align: center;
  border: 2px solid grey;
  border-collapse: collapse;
  padding: 5px;
  background-color: #ff6600;
  width: 35vw;
  height: 5vh;
  position: sticky;
  top: 0;
  font-size: 2rem;
`;

export const TR = styled.tr`
  width: 35vw;
  height: 5vh;
  font-size: 1.5rem;
  font-weight: bold;
  &:nth-child(even) {
    background: #ccc;
  }
  &:nth-child(odd) {
    background: #fff;
  }
`;

export const TD = styled.td`
  border: 1px solid grey;
  border-collapse: collapse;
  padding: 5px;
  text-align: center;
  width: 35vw;
`;
