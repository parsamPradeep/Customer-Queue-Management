import { Wrapper, Depth, Stats } from "./QueueDepth.styles";

const QueueDepth = (props) => {
  return (
    <Wrapper>
      <Depth primary>
        {props.qDepth1}
        <p>{props.qDepthName1}</p>
      </Depth>
      <Stats>
        <p>
          {props.qstats1} -: Total number serverd today :- {props.qstats2}
        </p>
        <p>
          {props.qstats3 + " ms"} -: Avarage time taken to serve :-{" "}
          {props.qstats4 + " ms"}
        </p>
      </Stats>
      <Depth>
        {props.qDepth2}
        <p>{props.qDepthName2}</p>
      </Depth>
    </Wrapper>
  );
};

export default QueueDepth;
