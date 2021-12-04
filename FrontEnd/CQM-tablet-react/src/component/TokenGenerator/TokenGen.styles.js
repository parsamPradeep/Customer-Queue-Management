import styled from "styled-components";

export const CardWrapper = styled.div`
  overflow: hidden;
  margin: 48px auto 0;
  width: 400px;
  font-family: Quicksand, arial, sans-serif;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.05), 0 0px 40px rgba(0, 0, 0, 0.08);
  border-radius: 5px;
`;

export const CardHeader = styled.header`
  padding: 32px 0;
`;

export const CardHeading = styled.h1`
  font-size: 24px;
  font-weight: bold;
  text-align: center;
`;

export const CardBody = styled.div`
  padding: 0 32px;
`;

export const CardFieldset = styled.fieldset`
  position: relative;
  border: 0;
  margin-top: 24px;
  margin-bottom: 5px;
`;

export const CardInput = styled.input`
  padding: 7px 0;
  width: 100%;
  font-family: inherit;
  font-size: 14px;
  border-top: 0;
  border-right: 0;
  border-left: 0;
  outline-style: solid;
  outline-width: thin;
  border-radius: 5px;
  transition: border-bottom-color 0.25s ease-in;

  &:focus {
    border-bottom-color: #e5195f;
  }
`;
export const CardSelect = styled.select`
  width: 100%;
  height: 35px;
  font-size: 14px;
  border: none;
  outline-style: solid;
  outline-width: thin;
  border-radius: 5px;
  option {
    color: black;
    display: flex;
    white-space: pre;
    min-height: 20px;
    font-weight: bold;
  }
`;

export const CardButton = styled.button`
  display: block;
  width: 100%;
  padding: 20px 0;
  font-family: inherit;
  font-size: 14px;
  font-weight: 700;
  color: #fff;
  background-color: #0053d9;
  border: 0;
  border-radius: 35px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.08);
  cursor: pointer;
  transition: all 0.25s cubic-bezier(0.02, 0.01, 0.47, 1);

  &:hover {
    box-shadow: 0 15px 15px rgba(0, 0, 0, 0.16);
    transform: translate(0, -5px);
  }
`;

export const Validation = styled.div`
  margin-top: 3%;
  width: 100%;
  font-family: inherit;
  font-size: 14px;
  color: red;
`;
