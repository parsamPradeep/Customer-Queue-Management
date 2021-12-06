import React from "react";
import QueueDepth from "../QueueDepth/index";
import { Swiper, SwiperSlide } from "swiper/react";
import SwiperCore, { Autoplay } from "swiper";
import "swiper/swiper-bundle.min.css";
import "swiper/swiper.min.css";
import { Wrapper } from "./SlidingWindow.styles";

SwiperCore.use([Autoplay]);

const SliderWindow = ({ serviceType, queueData, statistics }) => {
  const serviceTypesChunks = serviceType
    .map((st) => ({
      ...st,
      depth: queueData[st.serviceTypeDescription] || 0,
      depthName: st.serviceTypeDescription || "none",
      totalNumberServiced:
        statistics[`${st.serviceTypeDescription}-TotalNumberServiced`] || 0,
      averageServiceCompletionTime:
        statistics[
          `${st.serviceTypeDescription}-AverageServiceCompletionTime`
        ] || 0,
    }))
    .reduce((acc, cur, i) => {
      const chunkIndex = Math.floor(i / 2);
      acc[chunkIndex] = !acc[chunkIndex] ? [cur] : [...acc[chunkIndex], cur];
      return acc;
    }, []);

  const dummyData = {
    averageServiceCompletionTime: 0,
    depth: 0,
    depthName: "none",
    serviceTypeDescription: "none",
    serviceTypeId: 0,
    totalNumberServiced: 0,
  };
  console.log(serviceTypesChunks);
  if (serviceTypesChunks.length !== 0)
    if (serviceTypesChunks[serviceTypesChunks.length - 1].length % 2 !== 0)
      serviceTypesChunks[serviceTypesChunks.length - 1].push(dummyData);

  return (
    <Wrapper>
      <Swiper
        modules={[Autoplay]}
        spaceBetween={50}
        slidesPerView={1}
        autoplay={{
          delay: 2500,
          disableOnInteraction: false,
        }}
        className="swiper"
      >
        {Object.keys(queueData).length == 0 ? (
          <SwiperSlide>
            <QueueDepth
              qDepth1={dummyData.depth}
              qDepthName1={dummyData.depthName}
              qDepth2={dummyData.depth}
              qDepthName2={dummyData.depthName}
              qstats1={dummyData.totalNumberServiced}
              qstats2={dummyData.totalNumberServiced}
              qstats3={dummyData.averageServiceCompletionTime}
              qstats4={dummyData.averageServiceCompletionTime}
            />
          </SwiperSlide>
        ) : (
          serviceTypesChunks.map(([s1, s2], i) => (
            <SwiperSlide>
              <QueueDepth
                key={i}
                qDepth1={s1.depth}
                qDepthName1={s1.depthName}
                qDepth2={s2.depth}
                qDepthName2={s2.depthName}
                qstats1={s1.totalNumberServiced}
                qstats2={s2.totalNumberServiced}
                qstats3={s1.averageServiceCompletionTime}
                qstats4={s2.averageServiceCompletionTime}
              />
            </SwiperSlide>
          ))
        )}
      </Swiper>
    </Wrapper>
  );
};

export default SliderWindow;
