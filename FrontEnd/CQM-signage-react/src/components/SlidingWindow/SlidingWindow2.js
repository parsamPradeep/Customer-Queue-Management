import React from 'react'
import QueueDepth from '../QueueDepth/index'
import { Swiper, SwiperSlide } from 'swiper/react'
import SwiperCore, { Autoplay } from 'swiper'
import 'swiper/swiper-bundle.min.css'
import 'swiper/swiper.min.css'
import { Wrapper } from './SlidingWindow.styles'

SwiperCore.use([Autoplay])

const mockServiceTypes = [
  { serviceTypeDescription: 'service-1', serviceTypeId: '123' },
  { serviceTypeDescription: 'service-2', serviceTypeId: '456' },
  { serviceTypeDescription: 'service-3', serviceTypeId: '678' },
  { serviceTypeDescription: 'service-4', serviceTypeId: '9' }
]
const mockQueueData = { 'service-1': 1, 'service-3': 3 }
const mockStatistics = {
  'service-1-TotalNumberServiced': 100,
  'service-1-AverageServiceCompletionTime': 10,
  'service-2-TotalNumberServiced': 200,
  'service-2-AverageServiceCompletionTime': 20,
  'service-3-TotalNumberServiced': 100,
  'service-3-AverageServiceCompletionTime': 10,
  'service-4-TotalNumberServiced': 200,
  'service-4-AverageServiceCompletionTime': 20
}

const SliderWindow = ({
  serviceTypes = mockServiceTypes,
  queueData = mockQueueData,
  statistics = mockStatistics
}) => {
  const serviceTypesChunks = serviceTypes
    .map(st => ({
      ...st,
      depth: queueData[st.serviceTypeDescription] || 0,
      depthName: st.serviceTypeDescription || 'none',
      totalNumberServiced:
        statistics[`${st.serviceTypeDescription}-TotalNumberServiced`] || 0,
      averageServiceCompletionTime:
        statistics[
          `${st.serviceTypeDescription}-AverageServiceCompletionTime`
        ] || 0
    }))
    .reduce((acc, cur, i) => {
      const chunkIndex = Math.floor(i / 2)
      acc[chunkIndex] = !acc[chunkIndex] ? [cur] : [...acc[chunkIndex], cur]
      return acc
    }, [])

  return (
    <Wrapper>
      <Swiper
        modules={[Autoplay]}
        spaceBetween={50}
        slidesPerView={1}
        autoplay={{
          delay: 2500,
          disableOnInteraction: false
        }}
        className='swiper'
      >
        {Object.keys(queueData).length === 0 ? (
          <SwiperSlide>
            <QueueDepth qDepth1={0} qDepth2={0} />
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
  )
}

export default SliderWindow
