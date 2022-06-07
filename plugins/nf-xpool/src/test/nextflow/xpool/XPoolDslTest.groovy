package nextflow.xpool

import nextflow.Channel
import nextflow.extension.ChannelExtensionDelegate
import spock.lang.Specification
import spock.lang.Timeout


/**
 * @author : jorge <jorge.aguilera@seqera.io>
 *
 */
@Timeout(10)
class XPoolDslTest extends Specification{

    def setup () {
        ChannelExtensionDelegate.reloadExtensionPoints()
    }

    def 'should perform a hi and create a channel' () {
        when:
        def SCRIPT = '''
            channel.xpool.reverse('hi!') 
            '''
        and:
        def result = new MockScriptRunner([:]).setScript(SCRIPT).execute()
        then:
        result.val == '!ih'
        result.val == Channel.STOP
    }

    def 'should store a goodbye' () {
        when:
        def SCRIPT = '''
            channel
                .of('Bye bye folks')
                .goodbye() 
            '''
        and:
        def result = new MockScriptRunner([:]).setScript(SCRIPT).execute()
        then:
        result.val == 'Bye bye folks'
        result.val == Channel.STOP

        and:
        XPoolExtension.goodbyeMessage == 'Bye bye folks'.toUpperCase()
    }
}
