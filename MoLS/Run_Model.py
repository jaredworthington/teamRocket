import MoLS
import matlab

My_test=MoLS.initialize()
My_test.MoLS('./MoLS/Weather','outputWeatherData',nargout=0)
My_test.terminate()    
