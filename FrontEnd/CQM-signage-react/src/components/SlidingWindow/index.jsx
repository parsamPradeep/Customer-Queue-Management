import React from "react";
import QueueDepth from "../QueueDepth/index";
import { Swiper, SwiperSlide } from "swiper/react";
import SwiperCore, { Autoplay } from "swiper";
import "swiper/swiper-bundle.min.css";
import "swiper/swiper.min.css";
import { Wrapper } from "./SlidingWindow.styles";

SwiperCore.use([Autoplay]);

const SliderWindow = (props) => {
  let depth1 = null,
    depth2 = null;

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
        {Object.keys(props.queueData).length == 0 ? (
          <SwiperSlide>
            <QueueDepth qDepth1={0} qDepth2={0} />
          </SwiperSlide>
        ) : (
          props.serviceType.map((m, i) => {
            if (depth1 && depth2) depth1 = depth2 = null;
            if (!depth1) depth1 = m.serviceTypeDescription;
            else depth2 = m.serviceTypeDescription;
            if (depth2 && depth1 == null && i == props.serviceType.length - 1)
              depth1 = 0;
            if (depth1 && depth2 == null && i == props.serviceType.length - 1)
              depth2 = 0;

            if (depth1 != null && depth2 != null) {
              return (
                <SwiperSlide key={props.serviceType["serviceTypeId"]}>
                  <QueueDepth
                    key={props.serviceType["serviceTypeId"]}
                    qDepth1={depth1 == 0 ? 0 : props.queueData[depth1]}
                    qDepthName1={depth1 == 0 ? "none" : depth1}
                    qDepth2={depth2 == 0 ? 0 : props.queueData[depth2]}
                    qDepthName2={depth2 == 0 ? "none" : depth2}
                    qstats1={
                      props.statistics[depth1 + "-TotalNumberServiced"]
                        ? props.statistics[depth1 + "-TotalNumberServiced"]
                        : 0
                    }
                    qstats2={
                      props.statistics[depth2 + "-TotalNumberServiced"]
                        ? props.statistics[depth2 + "-TotalNumberServiced"]
                        : 0
                    }
                    qstats3={
                      props.statistics[depth1 + "-AverageServiceCompletionTime"]
                        ? props.statistics[
                            depth1 + "-AverageServiceCompletionTime"
                          ]
                        : 0
                    }
                    qstats4={
                      props.statistics[depth2 + "-AverageServiceCompletionTime"]
                        ? props.statistics[
                            depth2 + "-AverageServiceCompletionTime"
                          ]
                        : 0
                    }
                  />
                </SwiperSlide>
              );
            }
          })
        )}
      </Swiper>
    </Wrapper>
  );
};

export default SliderWindow;
