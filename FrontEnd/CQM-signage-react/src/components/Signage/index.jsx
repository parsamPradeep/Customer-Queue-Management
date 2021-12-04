import { Wrapper } from "./Signage.styles";
import Organisation from "../Organisation";
import SliderWindow from "../SlidingWindow";
import DisplayTable from "../Table";
import TextToSpeech from "../TextToSpeech";
import Advertise from "../Advertise";
import { useState, useEffect } from "react";
import SockJs from "sockjs-client";
import Stomp from "stompjs";
import { getServiceTypesForBranch } from "../../api/APIUtils";

const Signage = () => {
  const [serviceType, setServiceType] = useState([]);
  const [queueData, setQueueData] = useState({});
  const [tableData, setTableData] = useState([]);
  const [statistics, setStatistics] = useState([]);
  useEffect(() => {
    fetchServiceTypes();
    socketSetUp();
  }, []);

  let fetchServiceTypes = async () => {
    const response = await getServiceTypesForBranch("001");
    setServiceType(response.data.data);
    console.log(serviceType);
  };
  let socketSetUp = () => {
    const header = {
      Accept: "application/json",
      "Content-Type": "application/json",
    };
    const socket = new SockJs("http://localhost:4070/queue");
    const stompClient = Stomp.over(socket);
    let timeStamp = new Date();
    stompClient.connect({ headername: header }, (timeStamp) => {
      stompClient.subscribe("/topic/queuedepth-001", (queueDepth) => {
        let parsedData = JSON.parse(queueDepth.body);
        setQueueData(parsedData);
        console.log(parsedData);
      });
      stompClient.subscribe("/topic/statistics-001", (response) => {
        let parsedData = JSON.parse(response.body);
        setStatistics(parsedData);
        console.log(statistics);
      });
      stompClient.subscribe("/topic/tabledata-001", (response) => {
        let parsedData = JSON.parse(response.body);
        setTableData(parsedData);
        console.log(parsedData);
      });
      stompClient.subscribe("/topic/announcement-001", (anounce) => {
        let anouncement = JSON.parse(anounce.body);
        console.log(anouncement);
      });
    });
  };
  return (
    <Wrapper>
      <div className="box-one">
        <Organisation />
      </div>
      <div className="box-table ">
        <DisplayTable tableData={tableData} />
      </div>
      <div>
        <TextToSpeech />
      </div>
      <div>
        <Advertise />
      </div>
      <div className="box-last">
        <SliderWindow
          serviceType={serviceType}
          queueData={queueData}
          statistics={statistics}
        />
      </div>
    </Wrapper>
  );
};

export default Signage;
