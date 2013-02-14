package problems

import singleStateMethods.IteratedLocalSearchRandomRestarts

class ILSOptimize {
	protected rand = new java.util.Random()
	Integer evalCount = 0
	Integer maxIterations = 1000
	Float lowerBound = 1
	Float upperBound = 50
	Float halfMutationRange = 10
	def smallProblem
	
	def create = {
		smallProblem=new LeadingOnes(numBits : 50 , maxIterations : 100)
		//smallProblem=new OnesMax(numBits:10, maxIterations:100)
		return new IteratedLocalSearchRandomRestarts()
	}
	
	def copy = { a -> new IteratedLocalSearchRandomRestarts(mutationRate: a.mutationRate) }
	
	def quality = { a ->
		println "evaluating quality of ILS_OPT, ${a.mutationRate}"
		++evalCount
		smallProblem.evalCount = 0
		def qual = smallProblem.quality(a.maximize(smallProblem))
		println("giving $qual to HC")
		return qual
	}
	
	def tweak = { a ->
		new IteratedLocalSearchRandomRestarts(mutationRate:(bound(a.mutationRate  + (2 * rand.nextFloat() - 1) * halfMutationRange)))
	}
	
	private bound(x) {
		if (x < lowerBound) {
			lowerBound
		} else if (x > upperBound) {
			upperBound
		} else {
			x
		}
	}
	
	def terminate = { a, q = quality(a) ->
		evalCount >= maxIterations || q == maximalQuality()
	}
	
	def maximalQuality = {
		smallProblem.maximalQuality()
	}
	
	String toString(){
		"ILS_OPT_${smallProblem.toString()}_$maxIterations"
	}

}
