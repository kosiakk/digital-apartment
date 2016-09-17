d <- read.csv('../data/result', sep=' ')
names(d)
sensorTypes <- unique(d$sensorType)
min(d$fileName)

sensorTypes
par(mfrow=c(2,2))

for (s in sensorTypes) {
  filtered <- d[d$sensorType == s,]
  dt <-as.POSIXct(as.numeric(as.character(filtered$fileName/1000)),origin="1970-01-01",tz="GMT")
  plot(dt, filtered$eventData, col = filtered$sensorId, xlab = s, xaxt='n')
  xtick<-seq(min(dt), max(dt), length.out = 10)
  axis(side=1, at=xtick, labels = FALSE)
  text(x=xtick,  par("usr")[3],  labels = xtick, srt = 90, pos = 1, xpd = TRUE)
}


par(mfrow=c(1,1))
bw <- d[d$sensorId == "bewegungsmelder_14",]

bw

plot(as.POSIXct(as.numeric(as.character(bw$fileName/1000)),origin="1970-01-01",tz="GMT"), bw$eventData , col = bw$sensorId, xlab = "bewegungsmelder", xaxt='n')
xtick<-seq(min(as.POSIXct(as.numeric(as.character(bw$fileName/1000)),origin="1970-01-01",tz="GMT")), max(as.POSIXct(as.numeric(as.character(bw$fileName/1000)),origin="1970-01-01",tz="GMT")), length.out = 10)
axis(side=1, at=xtick, labels = FALSE)
text(x=xtick,  par("usr")[3],  labels = xtick, srt = 90, pos = 1, xpd = TRUE)

library(markovchain)

Bew6 <- d[d$sensorType == "bewegungsmelder" & d$sensorId == "bewegungsmelder_6" & d$eventType == "bewegung",]
Bew14 <- d[d$sensorType == "bewegungsmelder" & d$sensorId == "bewegungsmelder_14" & d$eventType == "bewegung",]
Bew15 <- d[d$sensorType == "bewegungsmelder" & d$sensorId == "bewegungsmelder_15" & d$eventType == "bewegung",]


Ko3 <- d[d$sensorType == "kontakt" & d$eventType == "offen" & d$sensorId == "kontakt_3",]
Ko9 <- d[d$sensorType == "kontakt" & d$eventType == "offen" & d$sensorId == "kontakt_9",]
Ko11 <- d[d$sensorType == "kontakt" & d$eventType == "offen" & d$sensorId == "kontakt_11",]
Ko13 <- d[d$sensorType == "kontakt" & d$eventType == "offen" & d$sensorId == "kontakt_13",]

out <- data.frame(ts = Bew6[Bew6$eventData == '1',]$fileName, status = 'Kitchen')
out <- rbind(out, data.frame(ts = Bew14[Bew14$eventData == '1',]$fileName, status = 'LivingRoom'))
out <- rbind(out, data.frame(ts = Bew15[Bew15$eventData == '1',]$fileName, status = 'Bedroom'))

out <- rbind(out, data.frame(ts = Ko3[Ko3$eventData == '1',]$fileName, status = 'FlatDoor'))
out <- rbind(out, data.frame(ts = Ko9[Ko9$eventData == '1',]$fileName, status = 'Fridge'))
out <- rbind(out, data.frame(ts = Ko11[Ko11$eventData == '1',]$fileName, status = 'BalcDoor'))
out <- rbind(out, data.frame(ts = Ko13[Ko13$eventData == '1',]$fileName, status = 'BedroomWin'))


out <- out[with(out, order(ts)), ]

mc.fit <- markovchainFit(data = out$status, method="mle", name="Event Sensor MLE")

summary(mc.fit)
plot(mc.fit$estimate)
rmarkovchain(n=1000, object=mc.fit$estimate, t0 = "FlatDoor") # show standard path...
