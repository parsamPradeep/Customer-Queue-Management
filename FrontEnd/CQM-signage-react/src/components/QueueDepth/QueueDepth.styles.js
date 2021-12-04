import styled from "styled-components";

export const Wrapper = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const Depth = styled.div`
  background: ${(props) => (props.primary ? "#ff6600" : "#ffd000")};
  margin: 1.5vh 0 0 0;
  width: 100px;
  height: 60px;
  text-align: center;
  padding: 25px 0 0 0;
  font-size: 2rem;
  font-weight: bold;
  p {
    font-size: 0.7rem;
  }
`;

export const Stats = styled.div`
  padding: 2vh 0 0 0;
  p {
    font-size: 1.5rem;
    font-weight: bold;
    line-height: 2rem;
    margin: 0;
  }
`;
