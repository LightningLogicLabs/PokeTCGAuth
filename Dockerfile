FROM openjdk:17-jdk
EXPOSE 9091:9091
RUN mkdir /app
COPY ./build/install/com.poketcgdb.poketcgauth/ /app/
WORKDIR /app/bin
CMD ["./com.poketcgdb.poketcgauth"]