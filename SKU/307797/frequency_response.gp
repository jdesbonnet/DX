#
# GnuPlot script to generate chart of frequency response curve.
# Joe Desbonnet, jdesbonnet@gmail.com, 14 Aug 2015.
#
set title "Frequency response of DX SKU 307797 (Ultrasonic Boosting Transformer)\nPrimary coil driven by 1V peak-to-peak sine wave"
set ylabel 'Secondary coil Vout (V)'
set xlabel 'Frequency (kHz)'
set yrange [0.1:10]
set xrange [30:4000]
set log x
set grid xtics ytics mxtics
set mxtics

set terminal pngcairo size 800,480
set output "frequency_response.png"


set label "Joe Desbonnet" at graph -0.07,-0.08 font ",8" 
set label "http://jdesbonnet.blogspot.com" at graph -0.07,-0.12 font ",8" 


plot 'frequency_response.dat' using 1:($2/1000) with linespoints linewidth 3 title ''

