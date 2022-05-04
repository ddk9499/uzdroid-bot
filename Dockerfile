FROM amazoncorretto:11.0.15
COPY /build/install/UzDroidBot /UzDroidBot
ENTRYPOINT /UzDroidBot/bin/UzDroidBot
