import { Wrapper, Image, Name, Time } from "./Organisation.styles";
import brand from "../../images/brand.jpg";
import { useState, useEffect } from "react";

const Organisation = () => {
  const [timer, setTimer] = useState("");
  useEffect(() => {
    setInterval(() => {
      let hr = new Date().getHours().toString();
      let min = new Date().getMinutes().toString();
      let sec =
        new Date().getSeconds() < 10
          ? "0" + new Date().getSeconds().toString()
          : new Date().getSeconds().toString();
      let timer = hr + ":" + min + ":" + sec;
      setTimer(timer);
    }, 1000);
  }, []);
  return (
    <Wrapper>
      <Image src={brand} alt="Organsation logo"></Image>
      <Name>
        <marquee>Your Organisation Name here</marquee>
      </Name>
      <Time>{timer}</Time>
    </Wrapper>
  );
};

export default Organisation;
