#
# GnuPlot script to generate chart of frequency response curve after adjusting
# the screwdriver adjustable lug. Three curves were generated using a script
# controling the WaveGen and measuring features of the InfiniiVision-3000 series 
# DSO-X 3014A.
# curve1.dat: lug flush with the casing (how it's shipped)
# curve2.dat: lug screwed as far as possible clockwise (fully in)
# curve3.dat: lug screwed anti-clockwise until it protrudes ~2mm above case
#
# Joe Desbonnet, jdesbonnet@gmail.com, 14 Aug 2015.
#
set title "Frequency response of DX SKU 307797 (Ultrasonic Boosting Transformer)\nPrimary coil driven by 1V peak-to-peak sine wave"
set ylabel 'Secondary coil Vout peak-to-peak (V)'
set xlabel 'Frequency (kHz)'
set yrange [0.1:10]
set xrange [60:4000]
set log x
set grid xtics ytics mxtics
set mxtics

set terminal pngcairo size 800,480
set output "frequency_response_2.png"


set label "Joe Desbonnet" at graph -0.07,-0.08 font ",8" 
set label "http://jdesbonnet.blogspot.com" at graph -0.07,-0.12 font ",8" 


plot \
'curve1.dat' using ($1/1000):($2/1) with lines linewidth 3 title 'lug flush with case', \
'curve2.dat' using ($1/1000):($2/1) with lines linewidth 3 title 'lug fully in (screwed clockwise)', \
'curve3.dat' using ($1/1000):($2/1) with lines linewidth 3 title 'lug protruding 2mm above case'
