import MoLS
import matlab

My_test=MoLS.initialize()
My_test.MoLS('./Weather',sys.argv[0],nargout=0)
My_test.terminate()    
