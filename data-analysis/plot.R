d <- read.csv('../data/result', sep=' ')
names(d)
sensorTypes <- unique(d$sensorType)

par(mfrow=c(5,2))

for (s in sensorTypes) {
  filtered <- d[d$sensorType == s,]
  plot(filtered$fileName, filtered$eventData, col = filtered$sensorId, xlab = s)
}
