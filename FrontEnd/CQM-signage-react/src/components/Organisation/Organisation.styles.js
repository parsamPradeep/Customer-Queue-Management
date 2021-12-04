import styled from "styled-components";

export const Wrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 0 0 5px 0;
`;

export const Image = styled.img`
  width: 15vw;
  height: 13vh;
  border: 1px solid grey;
`;

export const Name = styled.div`
  border: 1px solid grey;
  width: 50vw;
  display: flex;
  justify-content: center;
  align-items: center;
  marquee {
    font-size: 1.5rem;
    font-weight: bold;
    margin: 0;
  }
`;
export const Time = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 15vw;
  height: 13vh;
  border: 1px solid grey;
  font-size: 2rem;
  font-weight: bold;
`;
