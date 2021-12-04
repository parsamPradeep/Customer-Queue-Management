import { Container } from "./Advertise.styles";
import { Swiper, SwiperSlide } from "swiper/react";
import SwiperCore, { Autoplay } from "swiper";
import "swiper/swiper-bundle.min.css";
import "swiper/swiper.min.css";

SwiperCore.use([Autoplay]);
const images = [
  "https://source.unsplash.com/random/760x210?sig=1",
  "https://source.unsplash.com/random/760x210?sig=2",
  "https://source.unsplash.com/random/760x210?sig=3",
];
const Advertise = () => {
  return (
    <Container>
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
        {images.map((m) => (
          <SwiperSlide>
            <img src={m} alt="No Image" />
          </SwiperSlide>
        ))}
      </Swiper>
    </Container>
  );
};

export default Advertise;
