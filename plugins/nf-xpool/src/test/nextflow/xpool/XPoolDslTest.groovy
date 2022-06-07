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
}
