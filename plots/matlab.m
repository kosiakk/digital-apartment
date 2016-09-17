bewegungsmelder = strcmp(sensorType, 'bewegungsmelder');
hour = mod(fileName, 24*60*60*1000)/1000/60/60;



for t = unique(eventType(bewegungsmelder))'
    this_evnt = t{1};
    
    disp( this_evnt )
    fltr = bewegungsmelder & strcmp(eventType, this_evnt);
    
    disp( sum(fltr) );
    
    figure; title( this_evnt ); hold all
    
    
        
    for this_id = unique(sensorId( bewegungsmelder & fltr ))'
        
        fltrId = this_id == sensorId;
        
        x = fileName(fltr & fltrId);
        y = eventData(fltr & fltrId);
        
        [x, idx] = sort(x); y=y(idx);
        
        x = mod(x, 24*60*60*1000)/1000/60/60;    % in hours
        
        plot(x, y, '.', 'DisplayName', sprintf('sensor %i', this_id) );
        
        
    end
    
    xlim([0 24]); grid on
end